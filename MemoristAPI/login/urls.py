from django.conf.urls import url
from login.views import *

urlpatterns = [
    url(r'^login/$', LoginAPIView.as_view()),
    url(r'^register/$', RegisterAPIView.as_view()),
    url(r'^verify_email/$', VerifyEmail.as_view(), name="emailverify"),
    url(r'^profile/$', UserProfileAPIView.as_view(), name="profile"),
    url(r'^profile_update/$', UserProfileUpdateAPIView.as_view(), name="profile-update"),
    url(r'^profile_photo/$', UploadProfilePhotoAPIView.as_view()),
    url(r'^get_profile_photo/$', GetProfilePhotoAPIView.as_view()),
    url(r'^follow/$', FollowAPIView.as_view()),
    url(r'^unfollow/$', UnfollowAPIView.as_view()),
    url(r'^get_followers/$', GetFollowersAPIView.as_view()),
    url(r'^get_followings/$', GetFollowingsAPIView.as_view()),

]
