
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

working_dir=/var/www

rm -rf $working_dir/test

read -p "project name: " project_name

project_dir=$working_dir/$project_name
mkdir $project_dir
mkdir $project_dir/public

sites=/etc/apache2/sites-available

conf_files_num=`find $sites/. -name "*.conf" -type f | wc -l`
((conf_files_num--))
printf -v conf_num "%03d" $conf_files_num

cp $working_dir/html/index.html $project_dir/public
cp $sites/000-default.conf $sites/$conf_num-$project_name.conf 
