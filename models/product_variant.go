package models

import (
	"time"
	"github.com/shopspring/decimal"
)

type ProductVariant struct {
	ID              string           `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	ProductID       string           `gorm:"type:uuid;not null;index" json:"productId"`
	VariantName     string           `gorm:"type:varchar(100)" json:"variantName"`
	VariantCode     string           `gorm:"type:varchar(100);unique" json:"variantCode"`
	Price           decimal.Decimal  `gorm:"type:decimal(38,2)" json:"price"`
	Stock           int              `json:"stock"`
	IsActive        bool             `gorm:"default:true" json:"isActive"`
	CreatedAt       time.Time        `gorm:"autoCreateTime" json:"createdAt"`
	UpdatedAt       time.Time        `gorm:"autoUpdateTime" json:"updatedAt"`
}

// Optional if you want to explicitly name the DB table
func (ProductVariant) TableName() string {
	return "product_variant"
}
