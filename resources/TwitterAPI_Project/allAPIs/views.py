from django.shortcuts import render,HttpResponse
import tweepy
import json
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

def get_trending_topics(name):
	"""
		Author: Yunus Ege Saygılı

		Returns top ten trending topics for the input location. If the location is invalid,
		returns the top ten trending topics worldwide.
	"""

	# set up the consumer key and access token (obtained from https://apps.twitter.com/app/new)
	consumer_key='Di6z6xXCwJrvduPmXSbNZdILG'
	consumer_secret='VgfCkLt8QCmmVmBa8VJss6FQiE866L3Bs5DDSm8M9k5s3IDjwE'

	access_token='121485173-PXoF8a0MkroQQisbN89BwntiLtZIe31nDZnXtJyH'
	access_token_secret='CtHbI0YnoMCXMOxcEDYQZTMFqCRgXlJHYd3Umu1Pjpf2i'


	#  get authorization from Twitter API using tweepy
	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	auth.secure = True

	api = tweepy.API(auth)

	places = api.trends_available()
	
	woeid = 1 # woeid for worldwide is 1

	for place in places: # search for the input parameter
		if place['name'] == name: # if found, get the id of the given location
			woeid = place['woeid']
			break

	trends = api.trends_place(woeid) # get the trending topics of the location
	trendlist = []

	for i in range(10) : # get the top ten tending topics
		trendlist.append(trends[0]['trends'][i]['name'])
	return trendlist

def block_user(theUser, theTarget):
	# author: Berke Esmer - 2015400021
	# block_user - params: theTarget user, the user who is logged on

	consumer_key='E5dLqlJ44RZMlM1EfJLH3mXOk'
	consumer_secret='ktjt6kviS5aMPitEgVHvt7zXYG7Iv9u6YQNnLZX51BByRvdtcl'

	access_token='907309399726854144-MDB2s6110TvpbjF6PRZzpSpScwblISA'
	access_token_secret='cseS87IorbQVceGGP3qUvGDBKeU0fS4gMFuvds8JfMhZL'

	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	api = tweepy.API(auth)

	#  get the values of the user, and the target

	api.create_block(id=theTarget, user_id=theUser)

