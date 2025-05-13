package com.example.indie91.Repositories;

import com.example.indie91.DTO.UserFullProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserProfileRepositoryCustomImpl implements UserProfileRepositoryCustom {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<UserFullProfileDTO> findUserFullProfile(UUID userId) {

        String sql = """
    SELECT 
      u.id AS userId,
      u.name AS userName,
      u.email,
      u.phone,
      u.avatar_url AS avatarUrl,

      ip.id AS influencerProfileId,
      ip.total_earnings AS totalEarnings,
      ip.current_earnings AS currentEarnings,
      ip.total_sales AS totalSales,
      ip.followers_count AS followersCount,
      ip.engagement_rate AS engagementRate,
      ip.brand_collaborations AS brandCollaborations,

      contents.contents AS contentsJson

    FROM app_user u
    LEFT JOIN influencer_profile ip ON u.id = ip.user_id
    LEFT JOIN LATERAL (
      SELECT json_agg(
        json_build_object(
          'contentId', c.id,
          'contentType', c.content_type,
          'contentUrl', c.url,
          'viewsCount', c.views_count,
          'likesCount', c.likes_count,
          'contentDescription', c.description,
          'tag', json_build_object(
            'tagId', t.id,
            'tagName', t.name
          ),
          'products', (
            SELECT json_agg(
              json_build_object(
                'productId', p.product_id,
                'productName', p.name,
                'caption', p.caption,
                'price', p.price,
                'brandId', b.id,
                'brandName', b.name,
                'productImages', (
                  SELECT COALESCE(json_agg(json_build_object(
                    'imageUrl', pi.image_url,
                    'altText', pi.alt_text
                  )), '[]'::json)
                  FROM product_image pi
                  WHERE pi.product_id = p.product_id
                ),
                'productMedia', (
                  SELECT COALESCE(json_agg(json_build_object(
                    'mediaId', pm.id,
                    'mediaUrl', pm.url
                  )), '[]'::json)
                  FROM product_product_media ppm
                  JOIN product_media pm ON pm.id = ppm.product_media_id
                  WHERE ppm.product_id = p.product_id
                )
              )
            )
            FROM content_products cp
            JOIN product p ON p.product_id = cp.product_id
            LEFT JOIN brand b ON b.id = p.brand_id
            WHERE cp.content_id = c.id
          )
        )
      ) AS contents
      FROM content c
      LEFT JOIN tag t ON t.id = c.tag_id
      WHERE c.influencer_id = ip.id
    ) AS contents ON TRUE
""";


        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            UserFullProfileDTO dto = new UserFullProfileDTO();
            dto.setUserId(UUID.fromString(rs.getString("userId")));
            dto.setUserName(rs.getString("userName"));
            dto.setEmail(rs.getString("email"));
            dto.setPhone(rs.getString("phone"));
            dto.setAvatarUrl(rs.getString("avatarUrl"));

            dto.setInfluencerProfileId(getUUID(rs.getString("influencerProfileId")));
            dto.setTotalEarnings(rs.getDouble("totalEarnings"));
            dto.setCurrentEarnings(rs.getDouble("currentEarnings"));
            dto.setTotalSales(rs.getInt("totalSales"));
            dto.setFollowersCount(rs.getInt("followersCount"));
            dto.setEngagementRate(rs.getDouble("engagementRate"));
            dto.setBrandCollaborations(rs.getInt("brandCollaborations"));

            dto.setContentId(getUUID(rs.getString("contentId")));
            dto.setContentType(rs.getString("contentType"));
            dto.setContentUrl(rs.getString("url"));
            dto.setViewsCount(rs.getInt("viewsCount"));
            dto.setLikesCount(rs.getInt("likesCount"));
            dto.setContentDescription(rs.getString("contentDescription"));

            dto.setTagId(getUUID(rs.getString("tagId")));
            dto.setTagName(rs.getString("tagName"));

            dto.setProductId(getUUID(rs.getString("productId")));
            dto.setProductName(rs.getString("productName"));
            dto.setCaption(rs.getString("caption"));
            dto.setPrice(rs.getDouble("price"));

            dto.setBrandId(getUUID(rs.getString("brandId")));
            dto.setBrandName(rs.getString("brandName"));

            dto.setMediaId(getUUID(rs.getString("mediaId")));
            dto.setMediaUrl(rs.getString("mediaUrl"));

            dto.setProductImageUrl(rs.getString("productImageUrl"));
            dto.setImageAltText(rs.getString("imageAltText"));

            return dto;
        });
    }

    private UUID getUUID(String str) {
        return str == null ? null : UUID.fromString(str);
    }
}
