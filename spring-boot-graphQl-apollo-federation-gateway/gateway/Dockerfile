# gateway/Dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package.json ./
RUN npm install
COPY index.js ./
EXPOSE 4005
CMD ["node", "index.js"]