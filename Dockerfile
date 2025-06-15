# Start from official Golang base image (optimized for small deploys)
FROM golang:1.23-alpine AS builder

# Install Git and certificates
RUN apk update && apk add --no-cache git ca-certificates

# Set working directory
WORKDIR /app

# Cache dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy the source code
COPY . .

# Build the Go binary
RUN CGO_ENABLED=0 GOOS=linux go build -o server .

# Final image
FROM alpine:latest

# Set working directory
WORKDIR /root/

# Copy binary from builder
COPY --from=builder /app/server .

# If using .env, copy it (or load it from secret manager in prod)
#COPY .env .env

# Expose port for Cloud Run (Fiber defaults to 3000, you can change it)
EXPOSE 3000

# Run the Go app
CMD ["./server"]
