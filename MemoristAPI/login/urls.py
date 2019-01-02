from django.conf.urls import url
from login.views import *

urlpatterns = [
    url(r'^login/$', LoginAPIView.as_view()),
    url(r'^register/$', RegisterAPIView.as_view()),
    url(r'^verify_email/$', VerifyEmail.as_view(), name="emailverify"),
    url(r'^profile/$', UserProfileAPIView.as_view()),
    url(r'^get_profile/(?P<pk>\d+)/$', GetUserProfileAPIView.as_view()),
    url(r'^profile_update/$', UserProfileUpdateAPIView.as_view()),
    url(r'^profile_photo/$', UploadProfilePhotoAPIView.as_view()),
    url(r'^get_profile_photo/$', GetProfilePhotoAPIView.as_view()),
    url(r'^follow/$', FollowAPIView.as_view()),
    url(r'^unfollow/$', UnfollowAPIView.as_view()),
    url(r'^get_followers/$', GetFollowersAPIView.as_view()),
    url(r'^get_followings/$', GetFollowingsAPIView.as_view()),
    url(r'^delete_profile_photo/$', DeleteProfilePhotoAPIView.as_view()),
    url(r'^user_search/(?P<pk>[a-zA-Z0-9_]+)/$', SearchUserAPIView.as_view()),
    url(r'^redirect/$', redirectpage.as_view())

]
