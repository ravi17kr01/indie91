package models

type ContentTag struct {
	ContentID string  `gorm:"type:uuid;primaryKey" json:"content_id"`
	TagID     string  `gorm:"type:uuid;primaryKey" json:"tag_id"`
}
