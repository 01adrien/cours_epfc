import axios from "axios";
import { google } from "googleapis";
import { Auth } from "googleapis";
import credentials from "./credentials.json" assert { type: "json" };
import * as path from "path";
import * as fs from "fs";

import { authenticate } from "@google-cloud/local-auth";

const API_KEY = "AIzaSyA6e6henphJO9Ogax5b9Bq8Y7VjM1BFbV8";

const CREDENTIALS_PATH = path.join(process.cwd(), "credentials.json");

const scopes = ["https://www.googleapis.com/auth/drive"];

const client = new google.auth.OAuth2(
  credentials.client_id,
  credentials.client_secret,
  credentials.redirect_uris
);

client.apiKey = API_KEY;

const ID_OF_THE_FOLDER = "1Er590rl2DvtjYaoq2IpU_cXye43grIuM";

const drive = google.drive({ version: "v3", auth: client });

drive.files.list(
  {
    q: `'${ID_OF_THE_FOLDER}' in parents`,
    // fields: "files(id)",
    media: {
      mimeType: "application/vnd.google-apps.folder",
    },
  },
  (err, res) => {
    if (err) throw err;
    const files = res.data.file;
    if (files) {
      files.map((file) => {
        console.log(file);
      });
    } else {
      console.log("No files found");
    }
  }
);
