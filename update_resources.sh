#!/bin/bash

COLORS_SOURCE_FILE="shared/resources/src/commonMain/moko-resources/colors/colors.xml"
STRINGS_SOURCE_FILE="shared/resources/src/commonMain/moko-resources/base/strings.xml"

COLORS_TARGET_FILE="androidApp/ui/src/main/res/values/colors.xml"
COLORS_KT_FILE="androidApp/ui/src/main/java/com.chatkmm/ui/Colors.kt"
COLORS_SWIFT_FILE="iosApp/iosApp/Resources/Colors.swift"

BUILD_DIR="shared/resources/build"

echo "Сколько у вас языков локализации в приложении? (ответ 1 цифра)"
read LANGUAGE_COUNT

if [ "$LANGUAGE_COUNT" -eq 0 ]; then
  echo "Локализация не будет выполнена, так как вы указали 0 языков."
  exit 0
fi

echo "Какие языки? (введите языковые коды через запятую без пробелов, например: en,ru,fr)"
read LANGUAGES

IFS=',' read -ra LANGUAGE_CODES <<< "$LANGUAGES"

STRINGS_TARGET_PATHS=("androidApp/ui/src/main/res/values/strings.xml")

for LANG_CODE in "${LANGUAGE_CODES[@]}"; do
  STRINGS_TARGET_PATHS+=("shared/resources/src/commonMain/moko-resources/$LANG_CODE/strings.xml")
done

copy_colors_file() {
  cp "$COLORS_SOURCE_FILE" "$COLORS_TARGET_FILE"
  if [ $? -eq 0 ]; then
    echo "Файл $COLORS_SOURCE_FILE успешно скопирован в $COLORS_TARGET_FILE"
  else
    echo "Ошибка при копировании $COLORS_SOURCE_FILE в $COLORS_TARGET_FILE"
  fi
}

update_colors_kt() {
  NEW_COLOR_NAME=$(grep -o 'name="[^"]*"' "$COLORS_SOURCE_FILE" | sed 's/name="\([^"]*\)"/\1/' | tail -n 1)

  if [ -z "$NEW_COLOR_NAME" ]; then
    echo "Не удалось найти новое имя цвета в $COLORS_SOURCE_FILE"
    return
  fi

  if grep -q "val $NEW_COLOR_NAME:" "$COLORS_KT_FILE"; then
    echo "Цвет $NEW_COLOR_NAME уже существует в $COLORS_KT_FILE"
    return
  fi

  sed -i '' "/abstract val transparent: Color/a\\
    abstract val $NEW_COLOR_NAME: Color
  " "$COLORS_KT_FILE"

  sed -i '' "/override val transparent: Color = Color.Transparent,/a\\
        override val $NEW_COLOR_NAME: Color = Color(MultiplatformResource.colors.$NEW_COLOR_NAME.getColor(context)),
  " "$COLORS_KT_FILE"

  if [ $? -eq 0 ]; then
    echo "Цвет $NEW_COLOR_NAME успешно добавлен в $COLORS_KT_FILE"
  else
    echo "Ошибка при добавлении цвета в $COLORS_KT_FILE"
  fi
}

update_colors_swift() {
  NEW_COLOR_NAME=$(grep -o 'name="[^"]*"' "$COLORS_SOURCE_FILE" | sed 's/name="\([^"]*\)"/\1/' | tail -n 1)

  if [ -z "$NEW_COLOR_NAME" ]; then
    echo "Не удалось найти новое имя цвета в $COLORS_SOURCE_FILE"
    return
  fi

  if grep -q "var $NEW_COLOR_NAME:" "$COLORS_SWIFT_FILE"; then
    echo "Цвет $NEW_COLOR_NAME уже существует в $COLORS_SWIFT_FILE"
    return
  fi

  sed -i '' "/class Colors {/a\\
    var $NEW_COLOR_NAME: SwiftUI.Color { fatalError(\"Must override\") }
  " "$COLORS_SWIFT_FILE"

  sed -i '' "/class LightColors: Colors {/a\\
    override var $NEW_COLOR_NAME: SwiftUI.Color { MultiplatformResource.colors.shared.$NEW_COLOR_NAME.getColor() }
  " "$COLORS_SWIFT_FILE"

  sed -i '' "/class DarkColors: Colors {/a\\
    override var $NEW_COLOR_NAME: SwiftUI.Color { MultiplatformResource.colors.shared.$NEW_COLOR_NAME.getColor() }
  " "$COLORS_SWIFT_FILE"

  if [ $? -eq 0 ]; then
    echo "Цвет $NEW_COLOR_NAME успешно добавлен в $COLORS_SWIFT_FILE"
  else
    echo "Ошибка при добавлении цвета в $COLORS_SWIFT_FILE"
  fi
}

copy_strings_file() {
  for TARGET_FILE in "${STRINGS_TARGET_PATHS[@]}"; do
    TARGET_DIR=$(dirname "$TARGET_FILE")
    if [ ! -d "$TARGET_DIR" ]; then
      mkdir -p "$TARGET_DIR"
    fi

    cp "$STRINGS_SOURCE_FILE" "$TARGET_FILE"
    if [ $? -eq 0 ]; then
      echo "Файл успешно скопирован в $TARGET_FILE"
    else
      echo "Ошибка при копировании в $TARGET_FILE"
    fi
  done
}

clean_build() {
  echo "Удаление папки build..."
  rm -rf "$BUILD_DIR"
  if [ $? -eq 0 ]; then
    echo "Папка build успешно удалена."
  else
    echo "Ошибка при удалении папки build."
  fi
}

rebuild_module() {
  echo "Пересборка модуля resource..."
  (./gradlew :shared:resources:build)
  if [ $? -eq 0 ]; then
    echo "Модуль resource успешно пересобран."
  else
    echo "Ошибка при пересборке модуля resource."
  fi
}

fswatch -o "$COLORS_SOURCE_FILE" "$STRINGS_SOURCE_FILE" | while read; do
  if [ -f "$COLORS_SOURCE_FILE" ]; then
    echo "Файл $COLORS_SOURCE_FILE изменён. Выполняется обновление..."
    copy_colors_file
    update_colors_kt
    update_colors_swift
  fi

  if [ -f "$STRINGS_SOURCE_FILE" ]; then
    echo "Файл $STRINGS_SOURCE_FILE изменён. Выполняется копирование..."
    copy_strings_file
  fi

  clean_build
  rebuild_module
done
