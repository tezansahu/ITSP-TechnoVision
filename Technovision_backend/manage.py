#!/usr/bin/env python
import os
import sys

if __name__ == "__main__":
    os.environ.setdefault("DJANGO_SETTINGS_MODULE", "Technovision_backend.settings")
    try:
        from django.core.management import execute_from_command_line
    except ImportError:
	try:
		import django
	except ImportError:
		raise ImportError(
		"Couldn't Import Django. Are you sure it's installed and "
		"available on your PYTHONPATH environment variable? Did you "
		"forget to activate a virtual environment?"
	)
	raise    



'''except ImportError as exc:
        raise ImportError(
            "Couldn't import Django. Are you sure it's installed and "
            "available on your PYTHONPATH environment variable? Did you "
            "forget to activate a virtual environment?"
        ) from exc
    execute_from_command_line(sys.argv)'''

