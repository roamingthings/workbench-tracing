FROM mongo

COPY init.json /init.json
CMD mongoimport --host text_db --db reach-engine --db text_library --collection texts --type json --file /init.json --jsonArray
