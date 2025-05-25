#!/bin/bash

echo ""
echo "🔁 Sending 100 requests..."
echo "----------------------------"

URL=$1

if [ -z "$URL" ]; then
  echo "❗ Please provide the URL as argument."
  echo "   Example:"
  echo "     bash load-test.sh http://localhost:8082/book/self-test"
  exit 1
fi

# Start time
start_time=$(date +%s%3N) # milliseconds

# Clean log file
> whoami_responses.txt

# Send 100 requests
for i in {1..100}
do
  response=$(curl -s "$URL")
  echo "$response" >> whoami_responses.txt
done

# End time
end_time=$(date +%s%3N)
duration=$((end_time - start_time))

# Count responses
instance1=$(grep -c "book-service-1" whoami_responses.txt)
instance2=$(grep -c "book-service-2" whoami_responses.txt)

# Show result
echo ""
echo "✅ Test completed in $duration ms"
echo ""
echo "📊 Results:"
echo "book-service-1 handled: $instance1 requests"
echo "book-service-2 handled: $instance2 requests"
echo "Total requests: $((instance1 + instance2))"
echo "Total time: ${duration} ms"