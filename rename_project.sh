#!/bin/bash

echo "Введите новое название проекта (например, Widmeyer Template):"
read PROJECT_NAME

PROJECT_NAME_NO_SPACES=$(echo "$PROJECT_NAME" | sed 's/ //g')

CURRENT_DIR=$(basename "$PWD")
NEW_DIR=${PWD%/*}/$PROJECT_NAME_NO_SPACES

echo "Переименовываем текущую папку проекта из $CURRENT_DIR в $PROJECT_NAME_NO_SPACES..."
mv "../$CURRENT_DIR" "$NEW_DIR"

if [ -f "$NEW_DIR/settings.gradle.kts" ]; then
  SETTINGS_FILE="$NEW_DIR/settings.gradle.kts"
elif [ -f "$NEW_DIR/settings.gradle" ]; then
  SETTINGS_FILE="$NEW_DIR/settings.gradle"
else
  echo "Файл settings.gradle или settings.gradle.kts не найден!"
  exit 1
fi

echo "Обновляем название проекта в $SETTINGS_FILE..."
sed -i '' "s/rootProject.name = \".*\"/rootProject.name = \"$PROJECT_NAME_NO_SPACES\"/" "$SETTINGS_FILE"

echo "Введите новое название модуля проекта (например, widmeyertemplate):"
read MODULE_NAME

OLD_PACKAGE="com.chatkmm"
NEW_PACKAGE="com.$MODULE_NAME"

echo "Поиск и замена пакета $OLD_PACKAGE на $NEW_PACKAGE по всему проекту..."

grep -rl "$OLD_PACKAGE" "$NEW_DIR" | while read FILE; do
  sed -i '' "s/$OLD_PACKAGE/$NEW_PACKAGE/g" "$FILE"
done

OLD_PACKAGE_DIR="$NEW_DIR/androidApp/ui/src/main/java/com.chatkmm"
NEW_PACKAGE_DIR="$NEW_DIR/androidApp/ui/src/main/java/com/$MODULE_NAME"

if [ -d "$OLD_PACKAGE_DIR" ]; then
  echo "Переименовываем папку $OLD_PACKAGE_DIR в $NEW_PACKAGE_DIR..."
  mv "$OLD_PACKAGE_DIR" "$NEW_PACKAGE_DIR"
else
  echo "Папка $OLD_PACKAGE_DIR не найдена!"
fi

find "$NEW_DIR" -type d -name "widmeyertemplate" | while read OLD_DIR; do
  NEW_DIR_NAME=$(echo "$OLD_DIR" | sed "s/widmeyertemplate/$MODULE_NAME/g")
  echo "Переименовываем папку $OLD_DIR в $NEW_DIR_NAME..."
  mv "$OLD_DIR" "$NEW_DIR_NAME"
done

echo "Проект и модуль успешно переименованы!"
