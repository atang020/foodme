import sys
from random import randrange

argc = len(sys.argv)
people = ["J", "Dennis", "Aaron", "Phillip", "Ryan", "Henry", "Pete", "Alex", "Payam", "Austin"]

def stderr(s):
	sys.stderr.write(s)

def stdout(s):
	sys.stdout.write(s)

def help():
	stderr("Usage: get-person.py [count]\n")
	stderr("Returns a random person from the group.\n")
	stderr("If count is specified, will return <count> random people from the group")
	sys.exit(-1)

def go(count):
	left = people[:] # Copy the array, don't want a reference
	for i in range(count):
		if (len(left)) == 0:
			left = people[:]
		chosen = randrange(0, len(left))
		stdout(left.pop(chosen) + "\n")

def main():
	count = 1

	if argc == 2:
		arg = sys.argv[1]
		if arg == "-h" or arg == "--help":
			help()
		else:
			count = int(arg)

	go(count)

if __name__ == "__main__":
	main()