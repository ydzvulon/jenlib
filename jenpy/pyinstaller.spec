# -*- mode: python ; coding: utf-8 -*-

# from.url=https://geekswithlatitude.readme.io/docs/create-exe-with-data-files

from pathlib import Path
import json

block_cipher = None

my_dir = Path('.')
config_path = my_dir / '_version' / 'pyinstaller_config.json'
# doc = yaml.load(config_path.read_text().strip(), Loader=yaml.FullLoader)
doc = json.loads(config_path.read_text().strip())

entry_point_path = doc['entry_point']
exe_name = doc['exe_name']
hiddenimports = doc.get('hiddenimports', [])

a = Analysis([entry_point_path],
             pathex=['.'],
             binaries=[],
             datas=[],
             hiddenimports=hiddenimports,
            #  hiddenimports=['pyspark'],
             hookspath=[],
             runtime_hooks=[],
             excludes=[],
             win_no_prefer_redirects=False,
             win_private_assemblies=False,
             cipher=block_cipher,
             noarchive=False)
pyz = PYZ(a.pure, a.zipped_data,
             cipher=block_cipher)

# a.datas += [
#     ('version/version_prefix.txt', 'version/version_prefix.txt', 'DATA'),
#     ('version/project_prefix.txt', 'version/project_prefix.txt', 'DATA'),
# ]

exe = EXE(pyz,
          a.scripts,
          a.binaries,
          a.zipfiles,
          a.datas,
          [],
          name=exe_name,
          debug=False,
          bootloader_ignore_signals=False,
          strip=False,
          upx=True,
          upx_exclude=[],
          runtime_tmpdir=None,
          console=True )
