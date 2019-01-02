from django.shortcuts import render
from django.views.generic import RedirectView
from rest_framework.generics import CreateAPIView, RetrieveAPIView, ListAPIView
from rest_framework.response import Response
from rest_framework import status as st, request
from rest_framework.views import APIView
from login import serializers as ls
from login import models as lm
from datetime import datetime, timedelta
from django.urls import reverse_lazy
from rest_framework import status
from rest_framework.permissions import IsAuthenticated
import hashlib
from random import randint
from django.utils.crypto import get_random_string
from .index import send_aws_email, render, send_aws_sms
from jinja2 import Environment
from django.db.models import Q
from django.shortcuts import redirect


def sendVerifyEmail(dt, request):
    data = {}
    data['to'] = dt['user']['email']

    encoded = (dt['user']['email'] + str(datetime.now()))
    hash_object = hashlib.sha1(encoded.encode())
    hex_dig = hash_object.hexdigest()

    url = "http://" + str(request._get_raw_host()) + str(reverse_lazy("emailverify")) + "?uh=" + str(hex_dig)
    data['url'] = url
    data['email'] = dt['user']['email']
    user = lm.RegisteredUser.objects.filter(email=dt['user']['email']).first()
    old_acts = lm.Activation.objects.filter(user=user)
    for act in old_acts:
        act.is_active = False
        act.save()

    activation = lm.Activation(
        user=user,
        code=str(hex_dig),
    )
    activation.save()

    EMAIL_HTML_TEMPLATE = '/home/ubuntu/memorist.com/email_templates/email_verify.html.tmpl'
    EMAIL_TXT_TEMPLATE = '/home/ubuntu/memorist.com/email_templates/email_verify.txt.tmpl'
    result_html = render(EMAIL_HTML_TEMPLATE, data)
    result_txt = render(EMAIL_TXT_TEMPLATE, data)

    subject_template = "Memorist sent you an Activation URL"
    subject = Environment().from_string(subject_template).render()
    if (send_aws_email(subject, data['to'], result_txt, result_html)):
        user.save()
        return Response({'status': 'ok', 'url': url}, status=status.HTTP_200_OK)
    return Response({'status': 'fail'}, status=status.HTTP_400_BAD_REQUEST)


class redirectpage(APIView):

    def get(self, *args, **kwargs):
        return redirect("http://memoristg4.herokuapp.com/")


class VerifyEmail(RedirectView):
    def get(self, request):
        uh = request.GET['uh']
        activation = lm.Activation.objects.filter(code=uh).first()

        if not activation:
            return Response({'status': 'fail', 'detail': 'Activation is not registered'}, status=status.HTTP_400_BAD_REQUEST)

        user = activation.user
        user.activeEmail_status = True
        user.save()

        return redirect("http://memoristg4.herokuapp.com/")


class RegisterAPIView(CreateAPIView):
    serializer_class = ls.RegisterSerializer
    queryset = lm.RegisteredUser.objects.all()

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = ls.RegisterSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            sendVerifyEmail(serializer.data, request)
            return Response(serializer.data, status=st.HTTP_201_CREATED)
        return Response(serializer.errors, status=st.HTTP_400_BAD_REQUEST)


class LoginAPIView(APIView):

    def post(self, *args, **kwargs):
        data = self.request.data
        serializer = ls.LoginSerializer(data=data)
        serializer.is_valid(raise_exception=True)
        return Response(serializer.data, status=st.HTTP_200_OK)


class UserProfileAPIView(RetrieveAPIView):
    permission_classes = IsAuthenticated,
    serializer_class = ls.UserSerializer

    def get(self, request, *args, **kwargs):
        user = lm.RegisteredUser.objects.filter(id=self.request.user.id)
        if user.exists():
            serializer = ls.UserSerializer(user.first())
            return Response(serializer.data)
        else:
            return Response({"Status": "User does not exist."}, status=st.HTTP_400_BAD_REQUEST)


class GetUserProfileAPIView(RetrieveAPIView):
    serializer_class = ls.UserSerializer

    def get(self, request, *args, **kwargs):
        user = lm.RegisteredUser.objects.filter(id=self.kwargs['pk'])
        if user.exists():
            serializer = ls.UserSerializer(user.first())
            return Response(serializer.data)
        else:
            return Response({"Status": "User does not exist."}, status=st.HTTP_400_BAD_REQUEST)


class UserProfileUpdateAPIView(APIView):
    permission_classes = IsAuthenticated,

    def post(self, *args, **kwargs):
        data = self.request.data
        user = self.request.user

        updated_user = lm.RegisteredUser.objects.get(id=user.id)
        if "first_name" in data:
            updated_user.first_name = data["first_name"]
        if "last_name" in data:
            updated_user.last_name = data["last_name"]
        if "location" in data:
            updated_user.location = data["location"]
        if "gender" in data:
            updated_user.gender = data["gender"]
        if "advanced_location" in data:

            pl = lm.PointLocation.objects.filter(id=updated_user.advanced_location_id)
            if pl.exists():
                pl.location_name = data["advanced_location"]["location_name"]
                pl.location_coordinate_latitude = data["advanced_location"]["location_coordinate_latitude"]
                pl.location_coordinate_longitude = data["advanced_location"]["location_coordinate_longitude"]
                pl.save()
            else:
                pl = lm.PointLocation(
                    location_name=data["advanced_location"]["location_name"],
                    location_coordinate_latitude=data["advanced_location"]["location_coordinate_latitude"],
                    location_coordinate_longitude=data["advanced_location"]["location_coordinate_longitude"]
                )
                pl.save()
                updated_user.advanced_location = pl
        updated_user.save()

        serializer = ls.UserSerializer(updated_user)
        return Response(serializer.data, status=st.HTTP_200_OK)


class UploadProfilePhotoAPIView(CreateAPIView):
    serializer_class = ls.ProfilePhotoSerializer
    permission_classes = IsAuthenticated,

    def post(self, request):
        user = lm.RegisteredUser.objects.filter(id=self.request.user.id).first()
        photo = lm.ProfilePhoto.objects.filter(user=user.id)
        if (photo.exists()):
            photo = photo.first()
        else:
            photo = lm.ProfilePhoto()
        photo.user = user
        photo.image = self.request.FILES.getlist("image")[0]
        photo.save()
        serializer = ls.ProfilePhotoSerializer(photo)
        return Response(serializer.data, status=st.HTTP_200_OK)


class GetProfilePhotoAPIView(ListAPIView):
    serializer_class = ls.ProfilePhotoSerializer

    def get_queryset(self):
        photo = lm.ProfilePhoto.objects.filter(user=self.request.user.id)
        if (photo.exists()):
            return photo
        else:
            return None


class FollowAPIView(CreateAPIView):
    serializer_class = ls.FollowSerializer

    permission_classes = IsAuthenticated,

    def post(self, request, *args, **kwargs):

        user = lm.RegisteredUser.objects.filter(id=self.request.user.id)
        if (user.exists()):
            user = user.first()
        else:
            return Response({"Status": "User does not exist."}, status=st.HTTP_400_BAD_REQUEST)
        followedUser = lm.RegisteredUser.objects.filter(id=self.request.data["id"])
        if (followedUser.exists()):
            followedUser = followedUser.first()
        else:
            return Response({"Status": "Followed User does not exist."}, status=st.HTTP_400_BAD_REQUEST)

        followLink = lm.Follow.objects.filter(follower=user, followed=followedUser)
        if (followLink.exists()):
            return Response({"Status": "User already follows the target user."}, status=st.HTTP_200_OK)

        followLink = lm.Follow(follower=user, followed=followedUser)
        followLink.save()

        serializer = ls.FollowSerializer(followLink)

        return Response(serializer.data, status=st.HTTP_200_OK)


class UnfollowAPIView(APIView):
    serializer_class = ls.FollowSerializer

    permission_classes = IsAuthenticated,

    def post(self, request, *args, **kwargs):

        user = lm.RegisteredUser.objects.filter(id=self.request.user.id)
        if (user.exists()):
            user = user.first()
        else:
            return Response({"Status": "User does not exist."}, status=st.HTTP_400_BAD_REQUEST)

        followedUser = lm.RegisteredUser.objects.filter(id=self.request.data["id"])
        if (followedUser.exists()):
            followedUser = followedUser.first()
        else:
            return Response({"Status": "Followed User does not exist."}, status=st.HTTP_400_BAD_REQUEST)

        followLink = lm.Follow.objects.filter(follower=user, followed=followedUser)
        if (followLink.exists()):
            followLink.delete()
        else:
            return Response({"Status": "User does not follow the target user."}, status=st.HTTP_200_OK)

        return Response(status=st.HTTP_200_OK)


class GetFollowersAPIView(ListAPIView):
    serializer_class = ls.FollowerSerializer

    def get_queryset(self):
        followLink = lm.Follow.objects.filter(followed=self.request.user.id)
        if (followLink.exists()):
            return followLink
        else:
            return None


class GetFollowingsAPIView(ListAPIView):
    serializer_class = ls.FollowedSerializer

    def get_queryset(self):
        followLink = lm.Follow.objects.filter(follower=self.request.user.id)
        if (followLink.exists()):
            return followLink
        else:
            return None


class DeleteProfilePhotoAPIView(APIView):

    def post(self, *args, **kwargs):
        userId = self.request.user.id
        user = lm.RegisteredUser.objects.filter(id=userId)
        if user.exists():
            user = user.first()
            profilePhoto = lm.ProfilePhoto.objects.filter(user=userId)
            if profilePhoto.exists():
                profilePhoto = profilePhoto.first().delete()
                return Response({"status": "ok"}, status=st.HTTP_200_OK)
            else:
                return Response({"Status": "Profile Photo does not exist."}, status=st.HTTP_200_OK)
        else:
            return Response({"Status": "User does not exist."}, status=st.HTTP_400_BAD_REQUEST)


class SearchUserAPIView(ListAPIView):
    serializer_class = ls.UserSearchSerializer

    def get_queryset(self):
        return lm.RegisteredUser.objects.filter(Q(username__icontains=self.kwargs['pk']) | Q(first_name__icontains=self.kwargs['pk']) | Q(last_name__icontains=self.kwargs['pk']))
