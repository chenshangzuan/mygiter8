#!/bin/bash
#
# Copyright (c) 2020-2021.
# OOON.ME ALL RIGHTS RESERVED.
# Licensed under the Mozilla Public License, version 2.0
# Please visit <http://ooon.me> or mail to zhaihao@ooon.me
#

#echo "修复 idea for play 的配置文件完成"
a=`grep -c '<option name="uri" value="\$PROJECT_DIR\$\/api" \/>' ../.idea/play2_project_settings.xml`

if [ "$a" -eq 1 ]; then
    echo "🔨 idea 配置文件存在错误，正在执行修复"
    gsed -i 's/<option name="uri" value="\$PROJECT_DIR\$\/api" \/>/<option name="uri" value="$PROJECT_DIR$\/..\/api" \/>/g' ../.idea/play2_project_settings.xml
    echo "✅ 修复完成，请重新运行"
    exit 2
fi
echo "✅ idea 配置文件无错误，即将开始运行"
exit 0