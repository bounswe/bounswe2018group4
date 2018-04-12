from django.shortcuts import render,HttpResponse
import tweepy
from tweepy import OAuthHandler

def retweet(request):
	consumer_key='LtUoLhvWzGOUODCpHkIW1cH7s'
	consumer_secret='o18cdHxf8YLgLaWJZZ8lMvcv2SACOAIJYZmZB5s7nxmPPHRgc3'

	access_token='984099024402894848-bTCwMxW8o04woXa6OAD2Y7PIoxy3TPb'
	access_token_secret='ENb06BGdQamMnFHaxqDeUFL8GzafGIylruSW0s3ubXgqs'

	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	api = tweepy.API(auth)

	#  get the values of the user, and the target

	#if 'user_id' in request.POST
	userid=request.POST['user_id']
	# block
	list=api.followers_ids(id=userid)

	return render(request,'followers_ids.html',{'list':list})

def home(request):
	return render(request,'home.html')


def followerid_index(request):
	return render(request,'followerid_index.html')
	# Create your views here.
