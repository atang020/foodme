#!/usr/bin/env python

import os, shutil

updatesDir = os.path.dirname(os.path.realpath(__file__))
resDir = os.path.join(updatesDir, 'res\\')
foodmeDir = os.path.dirname(os.path.dirname(updatesDir))
gitHookDir = os.path.join(foodmeDir, '.git\\hooks\\')

def main():
	shutil.copy(os.path.join(resDir, 'post-merge'), gitHookDir)

if __name__ == '__main__':
	main()