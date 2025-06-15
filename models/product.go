package models

import (
	"github.com/shopspring/decimal"
)

//GORM maps struct names to snake_case plural table names by default e.g., products for Product struct.
type Product struct {
	ProductID        string              `gorm:"type:uuid;default:gen_random_uuid();primaryKey;column:product_id" json:"productId"`
	Name             string              `gorm:"type:varchar(150)" json:"name"`
	Caption          *string             `json:"caption,omitempty"`
	IsActive         *bool               `gorm:"default:true;column:is_active" json:"isActive,omitempty"`
	BrandID          string              `gorm:"type:uuid;column:brand_id" json:"brandId"`
	Slug             string              `gorm:"unique" json:"slug"`
	Price            *decimal.Decimal    `gorm:"type:decimal(38,2)" json:"price,omitempty"`
	ViewCount        *int                `gorm:"column:view_count" json:"viewCount,omitempty"`
	LikeCount        *int                `gorm:"column:like_count" json:"likeCount,omitempty"`
	ShareCount       *int                `gorm:"column:share_count" json:"shareCount,omitempty"`
	DiscountedPrice  *decimal.Decimal    `gorm:"type:decimal(38,2)" json:"discountedPrice,omitempty"`
}

// This method tells GORM explicitly which table name in the database should map to your Go struct
// This overrides GORM's default behavior and says: “Use product (singular) as the DB table name.”
func (Product) TableName() string {
	return "product"
}
