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

    def test_register_with_no_first_name(self):
        """Ensure that registering with no first_name or empty first_name results in 400 status code."""
        url = '/auth/register'
        credentials = {'last_name': 'hoassadcam', 'email': 'bahadir@hocamoglu.co', 'phone_number': '121445667', 'password': 'asdf1aa234', 'country_code': '91'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 400)

        credentials['first_name'] = ""
        self.assertEqual(response.status_code, 400)

    def test_register_with_invalid_email(self):
        """Ensure that registering a user with invalid email results in 400 status code."""
        url = '/auth/register'
        credentials = {'first_name': 'bahadir', 'last_name': 'hoassadcam', 'email': 'bahadirhocamoglu.co', 'phone_number': '121445667', 'password': 'asdf1aa234', 'country_code': '91'}
        response = APIClient().post(url, credentials)
        self.assertEqual(response.status_code, 400)
