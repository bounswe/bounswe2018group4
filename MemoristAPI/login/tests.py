from django.test import TestCase
from rest_framework.test import APIClient, APITestCase

import utils


class StaffModelTests(TestCase):
    fixtures = "dumpdata.json",

    def setUp(self):
        url = '/auth/register'
        credentials = {'first_name': 'emre', 'last_name': 'koc', 'email': 'emreh134150@gmail.com', 'password': '1234qwer', 'username': 'emrekoc'}
        self.client = utils.register_user(self.client, url, credentials)

    def test_register_with_success(self):
        """Ensure user can register with correct request."""
        url = '/auth/register'
        credentials = {'first_name': 'emre', 'last_name': 'koc', 'email': 'emreh134150@gmail.com', 'password': '1234qwer', 'username': 'emrekoc'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 201)

    def test_register_twice(self):
        """Ensure that registering with same email twice results in 400 status code."""
        url = '/auth/register'
        credentials = {'first_name': 'emre', 'last_name': 'koc', 'email': 'emreh134150@gmail.com', 'password': '1234qwer', 'username': 'emrekoc'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 400)

    def test_comment_to_memory(self):
        """Ensure that you can comment on an existing memory"""
        url = '/post/create_comment/164/'
        credentials = {'comment': 'hellooo'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 200)

    def test_comment_to_not_exist_memory(self):
        """Ensure that you can not comment on a non-existing memory"""
        url = '/post/create_comment/164/'
        credentials = {'comment': 'hellooo'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 400)

    def test_get_user_memory_list(self):
        """Get your memories """
        url = '/post/list/'
        response = APIClient().get(url)
        self.assertEqual(response.status_code, 200)

    def test_follow_user(self):
        url = '/auth/follow/'
        credentials = {'id': 3}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 200)

    def test_unfollow_user(self):
        url = '/auth/follow/'
        credentials = {'id': 3}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 200)

    def test_search_user(self):
        """search user with 'mr' keyword """
        url = '/auth/user_search/mr//post/like_post/1/'
        response = APIClient().get(url)
        self.assertEqual(response.status_code, 200)

    def test_like_memory(self):
        url = '/post/like_post/1/'
        response = APIClient().get(url)
        self.assertEqual(response.status_code, 200)
