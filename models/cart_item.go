package models

import (
	"github.com/shopspring/decimal"
)

type CartItem struct {
	ID        string              `gorm:"type:uuid;default:uuid_generate_v4();primaryKey"`
	Price     *decimal.Decimal    `gorm:"type:decimal(38,2)" json:"price,omitempty"`
	Quantity  *int64              `gorm:"type:bigint" json:"quantity,omitempty"`
	OrderID   string              `gorm:"type:uuid;not null" json:"order_id"`
	ProductID string              `gorm:"type:uuid;not null" json:"product_id"`
	UserID    string              `gorm:"type:uuid;not null" json:"user_id"`

	// Relations (optional for preload)
	AppUser  AppUser  `gorm:"foreignKey:UserID;references:ID;constraint:OnDelete:CASCADE;" json:"-"`
	Order    Order    `gorm:"foreignKey:OrderID;references:ID;" json:"-"`
	Product  Product  `gorm:"foreignKey:ProductID;references:ProductID;constraint:OnDelete:CASCADE;" json:"-"`
}

func (CartItem) TableName() string {
	return "cart_items"
}
