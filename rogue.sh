#!/bin/bash

if [ "$1" != "" ] && [ "$2" != "" ] && [ "$3" != "" ]; then
    input_file=$1
    output_file=`echo $1 | sed "s/-template/-$3/"`
    json_config=$2
    
    # Read the input file into a variable
    file_content=$(<"$input_file")
    
    # Iterate over key-value pairs in the JSON configuration
    for key in $(echo "$json_config" | sed 's/[{}]//g; s/,/\n/g' | grep -Eo '"[^"]+"|[^,]+' | sed 's/"//g'); do
        old_value=`echo "$ {$(echo "$key" | cut -d: -f1)}" | tr -d '[:space:]'`
        new_value="$(echo "$key" | cut -d: -f2)"
        
        # Use parameter expansion to replace old_value with new_value
        file_content=${file_content//$old_value/$new_value}
    done
    
    # Save the modified content to the output file
    echo "$file_content" > "$output_file"
    
    echo "Setup config completed ✓✓"
    echo "Location config: $output_file"
    
else
    echo "Required argument [1,2,3]"
    echo "[1] = Path application template properties for spring boot"
    echo "[2] = Json stringify project config."
    echo "[3] = Active profile environment."
fi