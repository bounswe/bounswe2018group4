from django.conf.urls import url
from login.views import LoginAPIView, RegisterAPIView, VerifyEmail

urlpatterns = [
    url('^login/$', LoginAPIView.as_view()),
    url('^register/$', RegisterAPIView.as_view()),
    url(r'^verify_email/$', VerifyEmail.as_view(), name="emailverify"),

]
