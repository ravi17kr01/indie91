package config

import (
	"context"
	"github.com/redis/go-redis/v9"
	"log"
)

var RedisClient *redis.Client
var Ctx = context.Background()

func InitRedis() {
	RedisClient = redis.NewClient(&redis.Options{
		Addr: "localhost:6379",
	})
	_, err := RedisClient.Ping(Ctx).Result()
	if err != nil {
		log.Println("Could not connect to Redis>>>>>>>>>>>>>>>>>>", err)
	}
}
