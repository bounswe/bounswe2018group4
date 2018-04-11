# author: Dilara Gökay
import tweepy  # for accessing Twitter API

def post(tweet):
	"""Post a tweet in MemoristBeaver."""
	
	consumer_key='5GNyqW1qeECVJ6m3dzx6QGD3f'
	consumer_secret='6Q7N0yZle5qvbDLqnIioRBXzkqODmSMdKSqxIHevrZKnHyWjkf'

	access_token='981859542555348992-4VIhOkbS4MJru964CPgrGFteVQIzj1p'
	access_token_secret='sUaIhvHW91KMgI1EqWijJzcbMMm6BLoSNd9HU76FCB4Go'

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)

	api = tweepy.API(auth)
	api.update_status(status=tweet)
