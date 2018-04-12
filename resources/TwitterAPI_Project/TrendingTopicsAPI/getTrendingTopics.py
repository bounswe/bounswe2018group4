
import tweepy
import json


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
