#!/usr/bin/env python

import os, subprocess

updatesDir = os.path.dirname(os.path.realpath(__file__))
toolsDir = os.path.dirname(updatesDir)
databaseTool = os.path.join(toolsDir, 'database.py')

def main():
	print "NOTICE: Please enter your MySQL root password"
	subprocess.call([databaseTool, 'import', '-s'], shell=True)

if __name__ == '__main__':
	main()