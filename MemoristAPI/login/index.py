#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys

sys.path.append('/utils/infra_modules')

import logging
from flask import Flask, request, send_from_directory, jsonify
from flask import render_template
from logging.handlers import RotatingFileHandler
import json

import os
import os.path
import jinja2
from jinja2 import Environment

import string
import random

import hashlib
import http.client as httplib

import boto3
from botocore.exceptions import ClientError

from django.views.decorators.csrf import csrf_exempt

app = Flask(__name__)
app.config['DEBUG'] = True
app.config['TEMPLATES_AUTO_RELOAD'] = True

app = Flask(__name__)

'''
msg_context = {
'dear' : 'Mustafa Yılmaz',
'msg' : 'Ali veli tarafından size mesaj yollandı. İndirim kuponunuz 2323322323'
}
EMAIL_TEMPLATE = 'email_templates/notify_buyer.tmpl'

result = render(EMAIL_TEMPLATE, msg_context)
print(result)
'''


def render(tpl_path, context):
    path, filename = os.path.split(tpl_path)
    return jinja2.Environment(
        loader=jinja2.FileSystemLoader(path or './')
    ).get_template(filename).render(context)


def validate_username_password(username, password):
    if username == 'tpp-webapi':
        hashvalue = hashlib.md5(str(password).encode('utf-8')).hexdigest()
        if (hashvalue == '1f5161af931f20f9d078b5f18eaa16a2'):
            return True
        return False


def send_aws_email(subject, to, txt_contents, html_contents, sender="MEMORIST"):
    AWS_REGION = "us-east-1"
    SENDER = sender + "<emreh134150@gmail.com>"

    CHARSET = "UTF-8"
    BODY_HTML = html_contents
    BODY_TEXT = txt_contents
    RECIPIENT = to
    SUBJECT = subject

    # Create a new SES resource and specify a region.
    client = boto3.client('ses', region_name=AWS_REGION)

    try:
        # Provide the contents of the email.
        response = client.send_email(
            Destination={
                'ToAddresses': [
                    RECIPIENT,
                ],
            },
            Message={
                'Body': {
                    'Html': {
                        'Charset': CHARSET,
                        'Data': BODY_HTML,
                    },
                    'Text': {
                        'Charset': CHARSET,
                        'Data': BODY_TEXT,
                    },
                },
                'Subject': {
                    'Charset': CHARSET,
                    'Data': SUBJECT,
                },
            },
            Source=SENDER,
            # If you are not using a configuration set, comment or delete the
            # following line
            # ConfigurationSetName=CONFIGURATION_SET,
        )
        return True
    # Display an error if something goes wrong. 
    except ClientError as e:
        print(e.response['Error']['Message'])
        return False
    else:
        print("Email sent! Message ID:"),
        print(response['ResponseMetadata']['RequestId'])
        return False


def send_aws_sms(message, phone):
    AWS_REGION = "us-east-1"
    PHONE = str(phone)
    MESSAGE = message
    client = boto3.client('sns', region_name=AWS_REGION)
    try:
        response = client.publish(
            PhoneNumber=PHONE,
            Message=MESSAGE,
            MessageAttributes={
                'string': {
                    'DataType': 'String',
                    'StringValue': 'String',
                }
            }
        )
        return True
    except ClientError as e:
        print(e.response['Error']['Message'])
        return False


def send_sms(header, message, phones):
    path = os.path.expanduser('~/.verimor/credentials')
    file = open(path, 'r')
    text = file.read()
    lines = text.split('\n')
    data = {}
    for line in lines:
        line = line.replace(" ", "")
        key, value = line.split('=')
        data[key] = value
    sms_msg = {
        'username': data['username'],  # https://oim.verimor.com.tr/sms_settings/edit adresinden öğrenebilirsiniz.
        'password': data['password'],  # https://oim.verimor.com.tr/sms_settings/edit adresinden belirlemeniz gerekir.
        'source_addr': header,  # Gönderici başlığı, https://oim.verimor.com.tr/headers adresinde onaylanmış olmalı, değilse 400 hatası alırsınız.
        'messages': [
            {'msg': message, 'dest': phones}
        ]
    }

    sms_msg = json.dumps(sms_msg)

    conn = httplib.HTTPConnection('sms.verimor.com.tr', 80)
    request_headers = {'Content-type': 'application/json'}
    conn.request("POST", "/v2/send.json", sms_msg, request_headers)
    response = conn.getresponse()
    print(response.status, response.reason)

    if response.status == 200:
        data = response.read()
        conn.close
        return data
    else:
        data = response.read()
        print(data)
        conn.close
        return False


def sendEmail(data):
    '''
    to = 'lxxx@gmail.com'
    
    msg_context = {
    'invitee' : 'Mustafa Yılmaz',
    'inviter': 'Ali Veli',
    'coupon_code': generate_coupon_code()
    }
    '''
    outdata = {}

    app.logger.info(data)
    jsondata = data

    access_is_ok = False
    if 'username' in jsondata and 'password' in jsondata:
        up_is_ok = validate_username_password(jsondata['username'], jsondata['password'])
        if (up_is_ok):
            access_is_ok = True

    if not access_is_ok:
        outdata['status'] = 'Access is denied'
        return json.dumps(outdata)

    msg_context = {}
    if 'invitee' in jsondata:
        msg_context['invitee'] = jsondata['invitee']
        if 'inviter' in jsondata:
            msg_context['inviter'] = jsondata['inviter']
            if 'to' in jsondata:
                msg_context['to'] = jsondata['to']
                if 'phone' in jsondata:
                    print("phone_exists")
                    msg_context['phone'] = jsondata['phone']
                msg_context['coupon_code'] = jsondata['discount_code']

    EMAIL_HTML_TEMPLATE = 'email_templates/notify_buyer.html.tmpl'
    EMAIL_TXT_TEMPLATE = 'email_templates/notify_buyer.txt.tmpl'
    result_html = render(EMAIL_HTML_TEMPLATE, msg_context)
    result_txt = render(EMAIL_TXT_TEMPLATE, msg_context)

    subject_template = "Troperty send you a sale discount"
    subject = Environment().from_string(subject_template).render()

    sms_text_template = "Tropeerty has just sent you a message. Your sale discount coupon code: {{coupon_code}}"
    sms_text = Environment().from_string(sms_text_template).render(coupon_code=msg_context['coupon_code'])

    print(sms_text)
    if send_aws_email(subject, msg_context['to'], result_txt, result_html):
        outdata['status'] = '1'

    if 'phone' in msg_context:
        print("aaaaa")
        source_addr = "WesterOps"
        message = sms_text
        dest = msg_context['phone']

        # campaign_id = send_sms(source_addr, message, dest)
        campaign_id = send_aws_sms(message, dest)
        if campaign_id == False:
            print("Mesaj gönderilemedi.")
        else:
            print("Kampanya ID:", campaign_id)

    return json.dumps(outdata)

    outdata['status'] = '0'
    return json.dumps(outdata)


if __name__ == "__main__":
    # app.run()
    handler = RotatingFileHandler('../log/web.log', maxBytes=100000000, backupCount=30)
    handler.setLevel(logging.INFO)
    app.logger.addHandler(handler)
    app.run(debug=True, port=5000)
