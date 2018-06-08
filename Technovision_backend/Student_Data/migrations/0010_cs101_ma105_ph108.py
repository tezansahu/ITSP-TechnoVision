# Generated by Django 2.0.5 on 2018-06-08 06:27

from django.db import migrations, models
import django.db.models.deletion
import django_mysql.models


class Migration(migrations.Migration):

    dependencies = [
        ('Student_Data', '0009_delete_ma106_date'),
    ]

    operations = [
        migrations.CreateModel(
            name='CS101',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('Dates', django_mysql.models.ListCharField(models.CharField(max_length=10), default=[], max_length=264, size=24)),
                ('Attendance', django_mysql.models.ListCharField(models.CharField(max_length=2), default=[], max_length=72, size=24)),
                ('student', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Student_Data.Student')),
            ],
        ),
        migrations.CreateModel(
            name='MA105',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('Dates', django_mysql.models.ListCharField(models.CharField(max_length=10), default=[], max_length=264, size=24)),
                ('Attendance', django_mysql.models.ListCharField(models.CharField(max_length=2), default=[], max_length=72, size=24)),
                ('student', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Student_Data.Student')),
            ],
        ),
        migrations.CreateModel(
            name='PH108',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('Dates', django_mysql.models.ListCharField(models.CharField(max_length=10), default=[], max_length=264, size=24)),
                ('Attendance', django_mysql.models.ListCharField(models.CharField(max_length=2), default=[], max_length=72, size=24)),
                ('student', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Student_Data.Student')),
            ],
        ),
    ]