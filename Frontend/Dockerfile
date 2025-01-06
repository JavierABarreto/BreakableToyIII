FROM node:20-alpine
WORKDIR /Frontend
COPY package.json .
RUN npm install
COPY . .
RUN npm run build
CMD [ "npm", "run", "preview" ]