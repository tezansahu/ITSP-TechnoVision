# Generated by Django 2.0.5 on 2018-06-06 05:15

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Student_Data', '0003_auto_20180605_1917'),
    ]

    operations = [
        migrations.CreateModel(
            name='Attendence',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('Date', models.CharField(max_length=20)),
                ('Status', models.CharField(max_length=1)),
            ],
        ),
    ]
