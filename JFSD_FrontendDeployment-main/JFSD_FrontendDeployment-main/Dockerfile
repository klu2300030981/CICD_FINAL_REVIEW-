# Stage 1: Build Stage
FROM node:20-alpine as build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json (or package*.json) 
# from the build context (which should be the frontend folder)
# This step failed previously because the files were not visible or accessible.
COPY package.json ./
COPY package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the frontend source code
COPY . .

# Assuming the frontend is a React or similar project,
# the build command is usually 'npm run build'
RUN npm run build

# Stage 2: Serve Stage (using a lightweight server for the static files)
FROM nginx:alpine

# Copy the build output from the build stage to the nginx content directory
COPY --from=build /app/build /usr/share/nginx/html

# Expose the port Nginx runs on
EXPOSE 80

# The default Nginx command starts the server
CMD ["nginx", "-g", "daemon off;"]