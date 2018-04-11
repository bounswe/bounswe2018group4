# author: Dilara Gökay
import tweepy

# set up the consumer key and access token (obtained from https://apps.twitter.com/app/new)
def post(tweet):
	consumer_key='5GNyqW1qeECVJ6m3dzx6QGD3f'
	consumer_secret='6Q7N0yZle5qvbDLqnIioRBXzkqODmSMdKSqxIHevrZKnHyWjkf'

	access_token='981859542555348992-4VIhOkbS4MJru964CPgrGFteVQIzj1p'
	access_token_secret='sUaIhvHW91KMgI1EqWijJzcbMMm6BLoSNd9HU76FCB4Go'

	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)

	api = tweepy.API(auth)
	api.update_status(status=tweet)

post("I can tweet using Twitter API. Yay!")