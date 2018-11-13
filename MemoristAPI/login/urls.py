from django.conf.urls import url
from login.views import LoginAPIView, RegisterAPIView, VerifyEmail, UserProfileAPIView, UserProfileUpdateAPIView

urlpatterns = [
    url(r'^login/$', LoginAPIView.as_view()),
    url(r'^register/$', RegisterAPIView.as_view()),
    url(r'^verify_email/$', VerifyEmail.as_view(), name="emailverify"),
    url(r'^profile/$', UserProfileAPIView.as_view(), name="profile"),
    url(r'^profile_update/$', UserProfileUpdateAPIView.as_view(), name="profile-update"),

]
