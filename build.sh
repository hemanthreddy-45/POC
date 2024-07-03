#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Print each command before executing it (useful for debugging)
set -x

# Install dependencies
pip install -r requirements.txt

# Run any necessary build commands
# Example: python setup.py install
