from django.conf.urls import url, include
from . import views
from rest_framework import routers

router = routers.DefaultRouter()

router.register('Student',views.StudentView)
router.register('Course',views.CourseView)
router.register('MA106',views.MA106View)
router.register('CS101',views.CS101View)
router.register('PH108',views.PH108View)
router.register('MA105',views.MA105View)

urlpatterns = [
    url(r'', include(router.urls))
]
