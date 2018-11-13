from django.shortcuts import render
from rest_framework.generics import CreateAPIView, RetrieveAPIView, ListAPIView
from rest_framework.response import Response
from rest_framework import status as st
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


class VerifyEmail(APIView):
    def get(self, request):
        uh = request.GET['uh']
        activation = lm.Activation.objects.filter(code=uh).first()

        if not activation:
            return Response({'status': 'fail', 'detail': 'Activation is not registered'}, status=status.HTTP_400_BAD_REQUEST)

        user = activation.user
        user.activeEmail_status = True
        user.save()

        return Response({'status': 'ok'}, status=status.HTTP_200_OK)


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


class UserProfileUpdateAPIView(APIView):
    permission_classes = IsAuthenticated,

    def patch(self, *args, **kwargs):
        data = self.request.data
        user = self.request.user
        serializer = ls.UserUpdateSerializer(user, data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data, status=st.HTTP_200_OK)
        return Response(serializer.errors, status=st.HTTP_400_BAD_REQUEST)
