package models

type ProductProductMedia struct {
	ID              int64         `gorm:"column:id;primaryKey;autoIncrement" json:"id"`
	ProductID       string        `gorm:"column:product_id;type:uuid" json:"product_id"`
	ProductMediaID  string        `gorm:"column:product_media_id;type:uuid" json:"product_media_id"`

	Product         Product       `gorm:"foreignKey:ProductID;references:ProductID;constraint:OnUpdate:CASCADE,OnDelete:CASCADE"`
	ProductMedia    ProductMedia  `gorm:"foreignKey:ProductMediaID;references:ID;constraint:OnUpdate:CASCADE,OnDelete:CASCADE"`
}

func (ProductProductMedia) TableName() string {
	return "product_product_media"
}
