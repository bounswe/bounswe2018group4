from django.shortcuts import render
from rest_framework.generics import CreateAPIView
from rest_framework.response import Response
from rest_framework.status import *
from rest_framework.views import APIView
from login.serializers import *


# Create your views here.

class RegisterAPIView(CreateAPIView):
    serializer_class = RegisterSerializer
    queryset = RegisteredUser.objects.all()


class LoginAPIView(APIView):

    def post(self, *args, **kwargs):
        data = self.request.data
        user = self.request.user
        serializer = LoginSerializer(data=data)
        if serializer.is_valid():
            return Response(serializer.data, status=HTTP_200_OK)
        else:
            return Response(serializer.errors, status=HTTP_400_BAD_REQUEST)
