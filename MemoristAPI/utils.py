from rest_framework.permissions import IsAdminUser
from rest_framework.views import APIView
from rest_framework.response import Response

import json
from rest_framework.test import APIClient
from django.http import JsonResponse

import boto3
from botocore.exceptions import ClientError

import os

import login.models as AuthModels

# dumpdata command: python manage.py dumpdata -e contenttypes -e admin -e auth.Permission --natural-foreign > dumpdata.json


feedback_email_part1 = r"""
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta charset="utf-8"> <!-- utf-8 works for most cases -->
    <meta name="viewport" content="width=device-width"> <!-- Forcing initial-scale shouldn't be necessary -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge"> <!-- Use the latest (edge) version of IE rendering engine -->
    <meta name="x-apple-disable-message-reformatting">  <!-- Disable auto-scale in iOS 10 Mail entirely -->
    <title></title>
    <style>
        html,
        body {
            margin: 0 auto !important;
            padding: 0 !important;
            height: 100% !important;
            width: 100% !important;
        }
        * {
            -ms-text-size-adjust: 100%;
            -webkit-text-size-adjust: 100%;
        }
        div[style*="margin: 16px 0"] {
            margin: 0 !important;
        }
        table,
        td {
            mso-table-lspace: 0pt !important;
            mso-table-rspace: 0pt !important;
        }
        table {
            border-spacing: 0 !important;
            border-collapse: collapse !important;
            table-layout: fixed !important;
            margin: 0 auto !important;
        }
        table table table {
            table-layout: auto;
        }
        a {
            text-decoration: none;
        }
        img {
            -ms-interpolation-mode:bicubic;
        }
        *[x-apple-data-detectors],
        .unstyle-auto-detected-links *,
        .aBn {
            border-bottom: 0 !important;
            cursor: default !important;
            color: inherit !important;
            text-decoration: none !important;
            font-size: inherit !important;
            font-family: inherit !important;
            font-weight: inherit !important;
            line-height: inherit !important;
        }
        .im {
            color: inherit !important;
        }
        .a6S {
           display: none !important;
           opacity: 0.01 !important;
		}
		img.g-img + div {
		   display: none !important;
		}
        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) {
            u ~ div .email-container {
                min-width: 320px !important;
            }
        }
        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) {
            u ~ div .email-container {
                min-width: 375px !important;
            }
        }
        @media only screen and (min-device-width: 414px) {
            u ~ div .email-container {
                min-width: 414px !important;
            }
        }
    </style>
    <style>
        .button-td,
        .button-a {
            transition: all 100ms ease-in;
        }
	    .button-td-primary:hover,
	    .button-a-primary:hover {
	        background: #555555 !important;
	        border-color: #555555 !important;
	    }
        @media screen and (max-width: 600px) {
            .email-container {
                width: 100% !important;
                margin: auto !important;
            }
            .fluid {
                max-width: 100% !important;
                height: auto !important;
                margin-left: auto !important;
                margin-right: auto !important;
            }
            .stack-column,
            .stack-column-center {
                display: block !important;
                width: 100% !important;
                max-width: 100% !important;
                direction: ltr !important;
            }
            .stack-column-center {
                text-align: center !important;
            }
            .center-on-narrow {
                text-align: center !important;
                display: block !important;
                margin-left: auto !important;
                margin-right: auto !important;
                float: none !important;
            }
            table.center-on-narrow {
                display: inline-block !important;
            }
            .email-container p {
                font-size: 17px !important;
            }
        }
    </style>
</head>
<body width="100%" style="margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #EEFFEE;">   
    <div style="display: none; font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;">
        &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;
    </div>
    <div style="text-align:left;padding:3em;">
        <p style="padding:1em;"><b>From: """
feedback_email_part2 = r"""</b></p><p style="padding:1em;"><b>Message: """
feedback_email_part3 = r"""</b></p></div></div></body></html>"""


def bytes_to_json(byte):
    """Converts a bytes input into JSON output, which is type of Dict.
    byte: type of bytes"""
    return json.loads(byte.decode('utf8').replace("'", '"'))


def register_user(client, url, credentials, token_prefix="JWT"):
    """
    Registers a new user with given credentials by POSTing request to given url.
    obj: Client
    url: Str
    credentials: Dict
    token_prefix: Str
    """
    client = APIClient()
    response = client.post(url, credentials)
    client.credentials(HTTP_AUTHORIZATION=token_prefix + " " + response.data['token'])
    return client


def is_permitted(permission_required_list, queryset):
    for id in permission_required_list:
        if not queryset.filter(id=id).exists():
            return False

    return True


def send_sms(sms):
    """sms: SmsModel instance"""
    AWS_REGION = "us-east-1"
    PHONE = sms.receiver.filter(is_active=True)
    MESSAGE = sms.body
    client = boto3.client('sns', region_name=AWS_REGION)

    def wrapper(number):
        receiver = str(number.phone_number)
        try:
            response = client.publish(
                PhoneNumber=receiver,
                Message=MESSAGE,
                MessageAttributes={
                    'string': {
                        'DataType': 'String',
                        'StringValue': 'String',
                    }
                }
            )
            return response
        except ClientError as e:
            return e.response['Error']['Message']

    return list(map(wrapper, PHONE))


def send_email(email):
    print("Sent email")
    return True


def send_feedback_email(feedback_instance):
    "subject, to, txt_contents, html_contents"
    AWS_REGION = "eu-west-1"
    SENDER = "WesterOps.com Feedback<admin@infra.birlikte.al>"

    email_and_phone_number = ""

    if feedback_instance["email"]:
        email_and_phone_number += feedback_instance["email"]
    if feedback_instance["phone_number"]:
        if email_and_phone_number:
            email_and_phone_number += " / "
        email_and_phone_number += str(feedback_instance["phone_number"])
    email_and_phone_number = ", " + email_and_phone_number

    CHARSET = "UTF-8"
    BODY_HTML = feedback_email_part1 + feedback_instance["name"] + email_and_phone_number + feedback_email_part2 + feedback_instance["message"] + feedback_email_part3
    BODY_TEXT = "txt_contents"
    RECIPIENTS = ["gokhan.celik@westerops.com", "muhammet.dalbasti@westerops.com"]
    SUBJECT = "A new feedback is received"

    # Create a new SES resource and specify a region.
    client = boto3.client('ses', region_name=AWS_REGION)

    try:
        # Provide the contents of the email.
        response = client.send_email(
            Destination={
                'ToAddresses': RECIPIENTS,
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
        return False

    return True


def send_welcome_mail_with_password(staff_instance, password, *args, **kwargs):
    print(f"Said 'hello' to the new staff, {staff_instance}, {password}.")
    return True


def get_owner_instance(user):
    """
    Returns the owner instance if the user is an owner. Returns None otherwise
    """
    try:
        return user.ownermodel
    except user.DoesNotExist:
        return None


def get_owner_or_staff(user):
    """
    Returns the owner or staff instance and a boolean value indicating whether the user is an owner.
    """
    try:
        return user.ownermodel, True
    except user.DoesNotExist:
        return user.staffmodel, False


def publish_backend():
    os.system("cd && bash rewest.sh")


# @permission_classes((IsAdminUser,))
# def publish_backend_endpoint(request, *args, **kwargs):
#     return JsonResponse({"user": f"{request.user}"})
#     if request.user.is_superuser:
#         publish_backend()
#         return JsonResponse({"status": "Things seem to be OK..."})
#     else:
#         return JsonResponse({"status": "You've failed."})

class PublishBackendAPIView(APIView):
    permission_classes = (IsAdminUser,)

    def get(self, request, *args, **kwargs):
        publish_backend()
        return Response({"status": "All is good."})


def get_permitted_contacts(user):
    """
    Returns the contact list that user is permitted to access.
    """
    try:  # the user is an owner
        user = user.ownermodel
        return AuthModels.ContactModel.objects.filter(owner=user)
    except user.DoesNotExist:  # the user is a staff
        user = user.staffmodel
        return user.permissioned_contacts.all()


def get_item_with_id_from_queryset(set, id):
    """
    Returns the instance with given id from given queryset, None otherwise.
    """
    item = set.filter(id=id)

    if item.exists():
        return item.first()
    else:
        return None


def dateFormat_hour(date):
    # d = datetime.datetime.strptime(date, '%Y-%m-%d')
    if date:
        return date.strftime('%m.%d.%Y %H:%M')
    else:
        return date
