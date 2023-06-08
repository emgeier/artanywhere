///**
// * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// *
// * This file is licensed under the Apache License, Version 2.0 (the "License").
// * You may not use this file except in compliance with the License. A copy of
// * the License is located at
// *
// * http://aws.amazon.com/apache2.0/
// *
// * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// * CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
//*/
//import { DynamoDBClient, PutItemCommand, PutItemCommandInput } from "@aws-sdk/client-dynamodb";
//import { marshall } = from "@aws-sdk/util-dynamodb";
//import * as fs from "fs"
//const client = new DynamoDBClient({ region: "us-east-2" });
//
//console.log("Importing data into DynamoDB. Please wait.");
//
//var allExhibitions = JSON.parse(fs.readFileSync('data2.json', 'utf8'));
//allExhibitions.forEach((exhibition: any) => {
//        const params: PutItemCommandInput = {
//        TableName: "Exhibitions",
//        Item: marshall({
//            "startDate": exhibition.aic_start_at,
//            "exhibitionName": exhibition.title,
//            "short_description": exhibition.short_description
//        })
//    };
//
//    const run = async function () {
//        try {
//            const results = await client.send(new PutItemCommand(params));
//            console.log(results);
//        } catch(err) {
//            console.error(err);
//        }
//    };
//
//    run();
//});
