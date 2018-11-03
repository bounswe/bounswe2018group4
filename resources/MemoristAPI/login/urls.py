from django.conf.urls import url
from login.views import LoginAPIView, RegisterAPIView
urlpatterns = [
    url('login', LoginAPIView.as_view()),
    url('register', RegisterAPIView.as_view())
]
