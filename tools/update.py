#!/usr/bin/env python

import os
import subprocess

toolsDir = os.path.dirname(os.path.realpath(__file__))
updateStatusLog = os.path.join(toolsDir, 'update_status.log')
updatesDir = os.path.join(toolsDir, 'updates\\')

def addUpdatedFile(name):
	with open(updateStatusLog, 'a') as f:
		f.write(name + '\n')

# Returns a list of updates that have already been run
def getUpdatedFiles():
	conent = []
	with open(updateStatusLog, 'a+') as f:
		content = f.read().splitlines()
	return content

def getUpdates():
	alreadyUpdated = getUpdatedFiles()
	updates = [f for f in os.listdir(updatesDir) if os.path.isfile(os.path.join(updatesDir,f)) and f not in alreadyUpdated]
	return updates

def runUpdate(f):
	subprocess.call([f], cwd=updatesDir, shell=True)
	addUpdatedFile(f)

def main():
	updates = getUpdates()
	for u in updates:
		print "Running " + u
		runUpdate(u)
	

if __name__ == '__main__':
	main()