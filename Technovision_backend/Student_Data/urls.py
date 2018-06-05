from django.conf.urls import url, include
from . import views
from rest_framework import routers

router = routers.DefaultRouter()

router.register('Student',views.StudentView)
router.register('Course',views.CourseView)
router.register('MA106',views.MA106View)


urlpatterns = [
    url(r'', include(router.urls))
]
