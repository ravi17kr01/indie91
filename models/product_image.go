package models

import (
	"time"
)

type ProductImage struct {
	ImageID     string    `gorm:"column:image_id;primaryKey;type:uuid" json:"image_id"`
	ProductID   string    `gorm:"column:product_id;type:uuid" json:"product_id"`
	ImageURL    string    `gorm:"column:image_url" json:"image_url"`
	AltText     *string   `gorm:"column:alt_text;type:varchar(150)" json:"alt_text,omitempty"`
	PrioritySeq *int      `gorm:"column:priority_seq" json:"priority_seq,omitempty"`
	CreatedAt   time.Time `gorm:"column:created_at;autoCreateTime" json:"created_at"`

	Product Product `gorm:"foreignKey:ProductID;references:ProductID;constraint:OnUpdate:CASCADE,OnDelete:CASCADE" json:"-"`
}

// TableName overrides the default table name used by GORM
func (ProductImage) TableName() string {
	return "product_image"
}
