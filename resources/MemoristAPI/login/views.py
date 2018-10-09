from django.shortcuts import render
from rest_framework.generics import CreateAPIView, RetrieveAPIView
from rest_framework.response import Response
from rest_framework.status import *
from rest_framework.views import APIView
from login.serializers import *
from rest_framework.permissions import AllowAny


# Create your views here.

class RegisterAPIView(CreateAPIView):
    serializer_class = RegisterSerializer
    queryset = RegisteredUser.objects.all()


class LoginAPIView(APIView):
    permission_classes = (AllowAny,)
    serializer_class = LoginSerializer

    def post(self, *args, **kwargs):
        data = self.request.data
        print (data)
        user = RegisteredUser.objects.filter(username=data['username']) | RegisteredUser.objects.filter(email=data['username'])
        if not user.exists():
            return Response({"detail": "Incorrect Credentials"}, status=HTTP_400_OK)
        user = user.first()
        data['username'] = user.email
        serializer = LoginSerializer(data=data)
        # print (serializer)
        serializer.is_valid(raise_exception=True)
        return Response(serializer.data, status=HTTP_200_OK)
        # else:
        #   return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)
