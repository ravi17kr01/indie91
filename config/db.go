package config

import (
	"fmt"
	"log"
	"os"

	"gorm.io/driver/postgres"
	"supabase-fiber-app/models"
	"gorm.io/gorm"
	"github.com/joho/godotenv"
)

var DB *gorm.DB

func InitDB() {
	//only in local
	err := godotenv.Load()
	if err != nil {
		log.Fatalf("Error loading .env file")
	}

	dsn := os.Getenv("DATABASE_URL")
	fmt.Println("Connecting to DB with DSN>>>>>>>>>>>>>>>", dsn)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		DisableForeignKeyConstraintWhenMigrating: true,
		SkipDefaultTransaction: true,
	})
	if err != nil {
		log.Fatalf("Failed to connect to database: %v", err)
	}

	DB = db

	sqlDB, err := DB.DB()
	if err != nil {
		log.Println("Failed to get DB instance: ", err)
	}
	if err := sqlDB.Ping(); err != nil {
		log.Println("Failed to ping database: ", err)
	}

    sqlDB.Exec(`ALTER TABLE orders DROP CONSTRAINT IF EXISTS uni_orders_user_id;`) // safe

	err = DB.AutoMigrate(
		&models.AppUser{},               // User - referenced by Orders, Addresses, etc.
		&models.Brand{},                 // Referenced by Product
		&models.Product{},              // Referenced by ContentProduct, ProductImage, etc.
		&models.Content{},              // Referenced by ContentProduct
		&models.ContentProduct{},       // Depends on Content, Product
		&models.InfluencerProfile{},    // Depends on User?
		&models.UserAddress{},          // Depends on AppUser
		&models.Order{},                // Depends on AppUser
		&models.Payment{},              // Depends on Order
		&models.ProductImage{},         // Depends on Product
		&models.ProductMedia{},         // Standalone or media for products
		&models.ProductProductMedia{},  // Mapping table between Product and ProductMedia
		&models.ProductVariant{},       // Depends on Product
		&models.Tag{},                  // Independent
		&models.CartItem{},             // Depends on Product, AppUser
	)
	if err != nil {
	    log.Println("AutoMigrate failed: ", err)
    }

	fmt.Println("Connected to Supabase Postgres!")
}
