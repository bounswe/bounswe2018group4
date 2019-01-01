from django.db.models import Q
from rest_framework import serializers
from login.models import *
from rest_framework_jwt.settings import api_settings


jwt_payload_handler = api_settings.JWT_PAYLOAD_HANDLER
jwt_encode_handler = api_settings.JWT_ENCODE_HANDLER


class UserSearchSerializer(serializers.ModelSerializer):
    class Meta:
        model = RegisteredUser
        fields = [
            "id",
            "username"
        ]


class UserSerializer(serializers.ModelSerializer):
    photo = serializers.SerializerMethodField()
    advanced_location = serializers.SerializerMethodField()

    class Meta:
        model = RegisteredUser
        fields = [
            "id",
            "username",
            "first_name",
            "last_name",
            "email",
            "gender",
            "location",
            "activeEmail_status",
            "photo",
            "advanced_location"
        ]

    def get_photo(self, obj):
        photo = ProfilePhoto.objects.filter(user=obj.id)
        if photo.exists():
            return photo.first().image.__str__()
        return None

    def get_advanced_location(self, obj):
        advanced_location = PointLocation.objects.filter(id=obj.advanced_location_id)
        return PointLocationSerializer(advanced_location, many=True).data


class UserUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = RegisteredUser
        fields = [
            "id",
            "first_name",
            "last_name",
            "gender",
            "location"
        ]


class LoginSerializer(serializers.ModelSerializer):
    token = serializers.CharField(allow_blank=True, read_only=True)
    user = UserSerializer(read_only=True)

    class Meta:
        model = RegisteredUser
        fields = [
            'token',
            'user',
            'email',
            'username',
            'password'
        ]
        extra_kwargs = {
            "password": {"write_only": True},
            "username": {"write_only": True},
            "email": {"write_only": True}
        }

    def validate(self, data):
        usernam = data["username"]
        user = RegisteredUser.objects.filter(email=usernam)
        if user.exists():
            password = data['password']
            user = user.first()
            if user.check_password(password):
                payload = jwt_payload_handler(user)
                token = jwt_encode_handler(payload)
                data["token"] = token
                data["user"] = user
                return data
            else:
                raise serializers.ValidationError({"detail": "Incorrect credentials"})
        else:
            raise serializers.ValidationError({"detail": "Incorrect credentials"})


class RegisterSerializer(serializers.ModelSerializer):
    token = serializers.CharField(allow_blank=True, read_only=True)
    user = UserSerializer(read_only=True)

    class Meta:
        model = RegisteredUser
        fields = [
            'token',
            'user',
            'first_name',
            'last_name',
            'username',
            'email',
            'password'
        ]
        extra_kwargs = {"password": {"write_only": True},
                        "first_name": {"write_only": True},
                        "last_name": {"write_only": True},
                        "username": {"write_only": True},
                        }

    def create(self, validated_data):
        try:
            first_name = validated_data['first_name']
            last_name = validated_data['last_name']
            email = validated_data['email']
            password = validated_data['password']
            username = validated_data['username']
        except:
            raise serializers.ValidationError({"detail": "Incorrect Data1111111"})

        user_obj = RegisteredUser(
            username=username,
            email=email,
            first_name=first_name,
            last_name=last_name
        )
        user = RegisteredUser.objects.filter(email=email).distinct()
        if not user:
            user_obj.set_password(password)
            user_obj.save()
        else:
            raise serializers.ValidationError({'detail': "This email has already been registered"})

        payload = jwt_payload_handler(user_obj)
        token = jwt_encode_handler(payload)

        data = {}
        data["token"] = token
        data["user"] = user_obj
        return data


class ProfilePhotoSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProfilePhoto
        fields = "__all__"


class FollowSerializer(serializers.ModelSerializer):
    class Meta:
        model = Follow
        fields = "__all__"


class FollowerSerializer(serializers.ModelSerializer):
    username = serializers.SerializerMethodField()

    class Meta:
        model = Follow
        fields = [
            "follower",
            "username"
        ]

    def get_username(self, obj):
        return obj.follower.username


class FollowedSerializer(serializers.ModelSerializer):
    username = serializers.SerializerMethodField()

    class Meta:
        model = Follow
        fields = [
            "followed",
            "username"
        ]

    def get_username(self, obj):
        return obj.followed.username


class PointLocationSerializer(serializers.ModelSerializer):
    class Meta:
        model = PointLocation
        fields = "__all__"
