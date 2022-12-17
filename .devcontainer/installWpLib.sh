#!/bin/bash

# Download the latest version of WPILib
curl -s https://api.github.com/repos/wpilibsuite/allwpilib/releases/latest | grep "browser_download_url.*tar.gz" | cut -d : -f 2,3 | tr -d \" | wget -qi -
# Extract the tarball
F=$(ls | grep WPILib_Linux-*.*.tar.gz)
echo "Extracting $F"
tar -xzf $F
echo "Deleting tar file"
rm $F
