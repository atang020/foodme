#!/usr/bin/env python

import sys, os
from subprocess import call

argc = len(sys.argv)
verbose_mode = False

def stderr(s):
	sys.stderr.write(s)

def stdout(s):
	sys.stdout.write(s)

def verbose(s):
	if verbose_mode:
		stderr(s + "\n")

def help(s):
	if s:
		stderr(s + "\n")
	stderr("Usage: database.py action [OPTION]...\n")
	stderr("Actions:")
	stderr("  import  import into your local database from sql files")
	stderr("  export  export from your local database into sql files")
	stderr("  create  create the database in your mysql isntance")
	stderr("If no options are provided, the script will import/export both data and structure\n")
	stderr("Options:\n")
	stderr("  -s, --structure  import/export only the database structure(no data)\n")
	stderr("  -d, --data       import/export only the database data(no structure)\n")
	stderr("  -v, --verbose    print extra information while running\n")
	stderr("  -h, --help       print this message\n")
	sys.exit(-1)

def test_create(paths):
	cmd = "sed 's/foodme/foodme_test/g' " + paths["structure"] + " | mysql --user root --password"
	verbose("  Running command: " + cmd)
	os.system(cmd)

def action_import(structure, data, paths):
	if structure:
		verbose("Will import structure")
		cmd = "mysql --user root --password < \"" + paths["structure"] + "\""
		verbose("  Running command: " + cmd)
		os.system(cmd)
		test_create(paths)
		verbose("  Done running import")
	if data:
		verbose("Will import data")
		cmd = "mysql --user root --password foodme < \"" + paths["data"] + "\""
		verbose("  Running command: " + cmd)
		os.system(cmd)
		verbose("  Done running command")

def action_export(structure, data, paths):
	if structure:
		stderr("Sorry, but exporting structure is not allowed\n")
	if data:
		verbose("Will export data")
		cmd = "mysqldump foodme --user root --password --no-create-info --skip-add-drop-table --result-file \"" + paths["data"] + "\""
		verbose("  Running command: " + cmd)
		os.system(cmd)
		verbose("  Done running export")

def action_create(paths):
	pass

def main(file_path):
	if argc == 1: # No action was provided
		help("ERROR: Must provide an action")

	action = str(sys.argv[1])
	structure = True
	data = True

	# Get options
	for i in range(2, argc):
		arg = sys.argv[i]
		if arg == "-s" or arg == "--structure":
			data = False
		elif arg == "-d" or arg == "--data":
			structure = False
		elif arg == "-v" or arg == "--verbose":
			global verbose_mode
			verbose_mode = True
		elif arg == "-h" or arg == "--help":
			help()
		else:
			help("ERROR: Invalid option: " + str(arg))

	# Handle paths
	verbose("File path: " + file_path)
	paths = {
		"structure": os.path.abspath(os.path.join(file_path, os.pardir, "server_structure.sql")),
		"data": os.path.abspath(os.path.join(file_path, os.pardir, "server_data.sql"))
	}
	verbose("Paths: " + str(paths))

	# Check the option
	verbose("Checking the action")
	if action == "import":
		action_import(structure, data, paths)
	elif action == "export":
		action_export(structure, data, paths)
	elif action == "create":
		action_create(paths)
	else:
		help("ERROR: The action you provided is invalid: " + action)

if __name__ == "__main__":
	pathname = os.path.dirname(sys.argv[0])
	file_path = os.path.abspath(pathname)
	main(file_path)