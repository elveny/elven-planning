- 【日志级别】
```
http://localhost:8800/elven-planning/manage/loggers/org.springframework.web
curl --location 'http://localhost:8800/elven-planning/manage/loggers/org.springframework.web' \
--header 'Content-Type: application/json' \
--data '{
"configuredLevel":"INFO"
}'
```

- 【Nacos 配置中心】
```
http://localhost:8800/planning-user/web/v1/test/config
```