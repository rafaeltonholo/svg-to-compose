#!/bin/sh
fn_main() {
  echo "Running pre-push hook"
  echo "Running tests..."
  ./gradlew cleanAllTests allTests
  if [ $? -ne 0 ]; then
    echo "Tests failed. Aborting push."
    exit 1
  fi
  echo "Tests passed. Pushing..."
  exit 0
}

fn_main
