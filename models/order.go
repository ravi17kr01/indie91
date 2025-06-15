package models

import (
	"time"
)

type Order struct {
	ID          string     `gorm:"type:uuid;primaryKey" json:"id"`
	Address     string     `gorm:"type:varchar(255)" json:"address,omitempty"`
	Date        *time.Time `gorm:"type:timestamp(6)" json:"date,omitempty"`
	Description string     `gorm:"type:varchar(255)" json:"description,omitempty"`
	Status      *int       `gorm:"type:smallint" json:"status,omitempty"`
	TotalAmount *int64     `gorm:"column:total_amount" json:"total_amount,omitempty"`
	TrackingID  *string    `gorm:"type:uuid;column:tracking_id" json:"tracking_id,omitempty"`
	UserID      *string    `gorm:"type:uuid;index" json:"user_id,omitempty"` 

	CartItems []CartItem `gorm:"foreignKey:OrderID;constraint:OnUpdate:CASCADE,OnDelete:SET NULL" json:"cart_items,omitempty"`
	AppUser   *AppUser   `gorm:"foreignKey:UserID;references:ID;constraint:OnUpdate:CASCADE,OnDelete:SET NULL" json:"app_user,omitempty"`
}

func (Order) TableName() string {
	return "orders"
}
